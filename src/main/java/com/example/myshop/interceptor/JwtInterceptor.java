package com.example.myshop.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import io.jsonwebtoken.Jwts;

@Component
public class JwtInterceptor implements HandlerInterceptor {

    // 🌟 必須拿跟剛剛 JwtService 一模一樣的密鑰字串，才能驗證防偽鋼印！
    private final String SECRET_KEY = "MysmartGridSuperSecretKeyForProtectingOurEnergySystem";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        
        // 1. 如果前端發送的是 OPTIONS 請求（瀏覽器跨域測試），直接放行，不要阻擋
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            return true;
        }

        // 2. 從前端請求的 Header 裡面，把名為 "Authorization" 的小格子拔出來
        String authHeader = request.getHeader("Authorization");

        // 3. 檢查手環有沒有帶、格式對不對（必須是 Bearer 開頭）
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 401 狀態碼
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json");
            response.getWriter().write("{\"status\": \"error\", \"message\": \"❌ 拒絕存取：請先登入並攜帶合法 Token 憑證！\"}");
            return false; // 🚫 沒帶手環，直接在門口踢出去，不准進入 Controller！
        }

        // 4. 確實有帶 Token，把開頭的 "Bearer " 七個字剪掉，只留下真正的亂碼部分
        String token = authHeader.substring(7);

        try {
            // 5. 拿出秘密鑰匙，解密比對防偽鋼印。如果過期、或者被篡改，這裡會直接噴 Exception
            Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token);
            
            return true; // ⭕ 驗證完全正確！守門員放行，允許進入 Controller 蓋充電站！
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json");
            response.getWriter().write("{\"status\": \"error\", \"message\": \"❌ 拒絕存取：您的 Token 憑證已過期或失效！\"}");
            return false; // 🚫 帶假手環，踢出去！
        }
    }
}
