package cn.fileserver.filter;

import cn.fileserver.utils.RsaUtils;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.jetty.http.HttpStatus;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class AuthFilter implements Filter  {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        //转换成httpservlet
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        //获取header信息
        String sid = request.getHeader("X-SID");
        String sign = request.getHeader("X-Signature");

        //判断是否有头信息
        if (sid == null || sign == null || sid.length() <= 0 || sign.length() <= 0) {
            log.info("非法请求，sid和signature为空");
            response.setStatus(HttpStatus.FORBIDDEN_403);
            return;
        }

        //解密对比签名
        try {
            String decryptStr = RsaUtils.decryptStr(sign);
            if (sid.equals(decryptStr)) {
                log.info("验证成功   sign: {}  signature解密后: {}", sid, decryptStr);
                filterChain.doFilter(request, response);
            }else{
                log.info("验证失败   sign: {}  signature解密后: {}", sid, decryptStr);
                response.setStatus(HttpStatus.FORBIDDEN_403);
                return;
            }
        } catch (Exception e) {
            log.info("解密失败 sign；{}  signature：{}", sid, sign);
            response.setStatus(HttpStatus.FORBIDDEN_403);
            return;
        }
    }

    @Override
    public void destroy() {

    }
}
