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
            <div style="width: 540px; border-top: 4px solid #02b875; border-bottom: 4px solid #02b875; margin: 100px auto; padding: 30px 0; box-sizing: border-box;">
            	<h1 style="margin: 0; padding: 0 5px; font-size: 28px; font-weight: 400;">
            		개발용 - <span style="color: #02b875;">인증코드</span> 안내입니다.
            	</h1>
            	<p style="font-size: 16px; line-height: 26px; margin-top: 50px; padding: 0 5px;">
            		안녕하세요, 외국인 유학생을 위한 서비스 Giggle 입니다.<br />
            		요청하신 인증코드가 생성되었습니다.<br />
            		아래 인증코드로 회원가입을 진행해주세요.<br />
            		감사합니다.
            	</p>
            	<p style="font-size: 16px; margin: 40px 5px 20px; line-height: 28px;">
            		인증코드: <br />
            		<span style="font-size: 24px;">${AuthenticationCode}</span>
            	</p>
            </div>
            """;

    private final JavaMailSender javaMailSender;

    public void sendAuthenticationCode(final String receiverAddress, final String authenticationCode) {
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            mimeMessage.setSubject("Discord 인증코드 안내");

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
