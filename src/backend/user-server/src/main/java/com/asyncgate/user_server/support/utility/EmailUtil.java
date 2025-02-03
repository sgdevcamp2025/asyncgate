package com.asyncgate.user_server.support.utility;

import com.asyncgate.user_server.exception.FailType;
import com.asyncgate.user_server.exception.UserServerException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmailUtil {

    private static final String AUTHENTICATION_CODE_TEMPLATE = """
                <div style="background-color: #2F3136; padding: 20px; border-radius: 8px; max-width: 400px; margin: auto; box-shadow: 0 4px 10px rgba(0, 0, 0, 0.3); text-align: center; font-family: Arial, sans-serif; color: #ffffff;">
                    <h1 style="color: #7289DA;">이메일 인증 코드</h1>
                    <p style="font-size: 14px; color: #B9BBBE;">안녕하세요! 아래의 인증 코드를 입력하여 이메일 인증을 완료하세요.</p>
                    <div style="font-size: 24px; font-weight: bold; background-color: #23272A; padding: 10px; border-radius: 5px; display: inline-block; margin: 15px 0;">${AuthenticationCode}</div>
                    <p style="font-size: 14px; color: #B9BBBE;">이 코드는 5분 후 만료됩니다.</p>
                    <p style="font-size: 14px; color: #B9BBBE;">도움이 필요하시면 지원팀에 문의하세요.</p>
                    <div style="margin-top: 20px; font-size: 12px; color: #72767D;">&copy; 2025 Your Company. 모든 권리 보유.</div>
                </div>
            """;

    private final JavaMailSender javaMailSender;

    public void sendAuthenticationCode(final String receiverAddress, final String authenticationCode) {
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            mimeMessage.setSubject("Verify Your Email for Discord");

            // HTML 기반 이메일 생성
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setFrom("Discord");
            mimeMessageHelper.setTo(receiverAddress);
            mimeMessageHelper.setText(AUTHENTICATION_CODE_TEMPLATE.replace("${AuthenticationCode}", authenticationCode), true);

            // 이메일 전송
            javaMailSender.send(mimeMessage);

        } catch (MessagingException e) {
            throw new UserServerException(FailType._SEND_EMAIL_ERROR);
        } catch (Exception e) {
            throw new UserServerException(FailType._UNKNOWN_ERROR);
        }
    }

}
