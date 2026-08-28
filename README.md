# ⚡ myshop-grid-system | 智慧電網 ESG 與高併發搶票綜合後端系統

[![Java](https://shields.io)](https://oracle.com)
[![Spring Boot](https://shields.io)](https://spring.io)
[![MySQL](https://shields.io)](https://mysql.com)
[![Redis](https://shields.io)](https://redis.io)

本專案是一個基於 **Java 17** 與 **Spring Boot 3.3.2** 構建的高效能、高安全企業級後端 API 系統。專案核心融合了兩大實戰商務場景：**「智慧電網與碳權管理系統 (ESG & IoT Backend)」** 與 **「微服務高併發門票搶票系統 (High Concurrency System)」**。

系統整合了 Redis 高速快取、資料庫悲觀鎖、JPA 效能調優、JWT 安全權限校驗及自動化異常攔截，全方位展現了現代軟體工程的架構設計與效能優化思維。

---

## 🛠️ 核心技術棧 (Tech Stack)

- **後端核心**：Java 17+ / Spring Boot 3.3.2 / RESTful API / Jakarta Validation
- **資料持久層**：Spring Data JPA (Hibernate) / MySQL 8.0
- **快取與記憶體資料庫**：Redis (StringRedisTemplate)
- **安全防護**：JSON Web Token (JWT) / BCrypt 金融級密碼雜湊加密
- **排程引擎**：Spring Task Scheduled (多執行緒異步數據模擬)
- **開發協作**：Git 版本控制 / GitHub Copilot (Prompt Engineering 協作)

---

## 🚀 核心專案模組與技術亮點

### 1. 智慧電網與碳權管理系統 (ESG & IoT Backend)
*   **JPA Join Fetch 效能調優**：針對「充電站 (Charger)」與「發電廠 (PowerPlant)」的多表聯查，手寫 `@Query("SELECT c FROM Charger c JOIN FETCH c.powerPlant")`。**徹底攻克 JPA 常見的 N+1 查詢惡夢**，將資料庫 I/O 次數降至唯一次，大幅降低 CPU 與連線池開銷。
*   **DTO 雙向關聯解耦**：在雙向關聯（`@OneToMany` / `@ManyToOne`）架構下，引入 `PowerPlantDTO`，**只提取充電站代號清單（`chargerStationIds`），完美切斷 Jackson 序列化的無限遞迴死迴圈**（StackOverflowError）。
*   **物聯網動態數據模擬排程**：實作 `@Scheduled(fixedRate = 5000)` 背景排程器。每 5 秒利用多執行緒自動更新本機部署的電池電量（SOC）與隨機震盪溫度（Temp），模擬真實工業物聯網（IIoT）數據的動態變動，並採用 `saveAll()` 進行高效批次持久化。
*   **大數據流量防禦盾**：全面引入 `Pageable` 與 `Sort` 分頁查詢機制（如 `/charger/page`），精準控管海量資料的回傳流量，**杜絕百萬級資料量下伺服器記憶體崩潰（OOM）的風險**。

### 2. 微服務門票搶票系統 (High Concurrency System)
*   **Redis 記憶體高速預扣**：引進 Redis 記憶體資料庫，利用 `decrement` 執行緒安全的原子指令在快取層進行庫存扣減。**在 0.001 秒內彈開 99% 的無效衝撞流量**，確保極限高併發下流量不穿透至傳統資料庫。
*   **實名制防黃牛刷單 (Redis Set)**：部署 Redis `Set` 集合管理實名制名冊，利用 `isMember()` 做到 O(1) 時間複雜度的極速校驗，實現「一人限購一張」的商務防禦邏輯，在快取層精準攔截惡意重複點擊請求。
*   **超賣漏洞終極防禦 (悲觀寫入鎖)**：在持久層儲存庫宣告 `@Lock(LockModeType.PESSIMISTIC_WRITE)`，強迫 MySQL 底層生成 `FOR UPDATE` 列級排他鎖。在快取失效或極限併發情境下進行最終兜底（Double Check），**確保資料庫數據絕對不超賣、數據強一致性**。

### 3. 金融級安全與異常防護網
*   **JWT 守門員架構**：自定義 `JwtInterceptor` 與 `WebConfig` 路由攔截器，精準防守 `/charger/add/**` 等核心寫入端點。主動處理瀏覽器跨域測試的 `OPTIONS` 請求，並在驗證失敗時回補合規的 `401 Unauthorized` JSON 數據。
*   **全域自動化校驗與異常攔截**：整合 Jakarta Validation 註解（如 `@NotBlank`, `@Min`），配合 `@ControllerAdvice` 與 `@ExceptionHandler` 集中捕捉並格式化 `MethodArgumentNotValidException` 異常，阻止不合規的髒資料污染業務層。

---

## 🗂️ 專案結構簡析

```text
src/main/java/com/example/myshop/
├── controller/          # 控管層：RESTful 端點路由、@Valid 參數觸發
│   └── grid/            # 包含 GlobalExceptionHandler 全域異常防禦盾
├── entity/              # 實體層：MySQL 資料庫映射與校驗規則、解耦 DTO
├── repository/          # 持久層：JPA 儲存庫，包含 @Lock 悲觀鎖、JOIN FETCH 優化 SQL
├── interceptor/         # 安全層：JwtInterceptor 憑證校驗守門員
└── service/             # 業務邏輯層：高併發搶票防火牆、排程數據模擬
```

---

## 🌐 API 核心路由設計

### 🔑 認證模組 (不需攜帶 Token)
- `POST /auth/register` : 管理員帳號註冊 (啟動 BCrypt 加密)
- `POST /auth/login` : 管理員登入認證 (簽發過期時間 2 小時之 HS256 JWT Token)

### ⚡ 智慧電網模組 (部分需權限)
- `POST /charger/add` : 🔒 新增充電站 (需攜帶 `Bearer Token`)
- `GET /charger/page` : 🔓 分頁與倒序查詢充電站列表 (杜絕 OOM 流量控管)
- `GET /powerplant/find-with-chargers/{plantId}` : 🔓 透過 DTO 斷迴圈架構查詢發電廠詳情

### 🎟️ 高併發搶票模組
- `POST /ticket/rush/{ticketId}` : 🔓 透過 Redis Set + 預扣 + MySQL 悲觀鎖進行極限搶票

---

## 🛠️ 本機啟動與運行指引

### 1. 前置準備
- 安裝 **JDK 17** 或更高版本
- 本機啟動 **MySQL 8.0** 並建立對應資料庫（設定詳見 `application.properties`）
- 本機啟動 **Redis Server**（預設埠號 6379）

### 2. 複製與編譯專案
```bash
# 複製專案
git clone https://github.com

# 進入專案目錄
cd myshop-grid-system

# 使用 Maven 編譯並運行專案
mvn spring-boot:run
```
