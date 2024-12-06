package org.dfq.webserver.service;

import org.dfq.webserver.models.User;
import org.dfq.webserver.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.dfq.webserver.security.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.DigestUtils;
import java.nio.charset.StandardCharsets;
import java.util.*;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    /**
     * 注册
     * @param user 用户类
     * @return ServiceRes
     */
    public ResponseEntity<String> register(User user) {

        // 判断用户名是否唯一
        if(this.checkUserNameIsUnique(user)) {

            // 判断用户名密码是否合法
            if(this.checkUserNameAndPassword(user)) {

                // 密码MD5加密
                user.setPassword(this.MD5Code(user.getPassword()));
                // 加入创建时间
                user.setCreateTime(new Date());
                // 入库
                userRepository.save(user);
                return new ResponseEntity<>("注册成功！", HttpStatus.OK);

            } else return ResponseEntity.status(600).body("密码不合法。");

        } else return ResponseEntity.status(601).body("用户名已存在。");

    }

    /**
     * 登录
     * @param user 用户类
     * @return ServiceRes
     */
    public ResponseEntity<String> login(User user) {

        // 判断用户名密码是否合法
        if(this.checkUserNameAndPassword(user)) {

            // 密码MD5加密
            user.setPassword(this.MD5Code(user.getPassword()));

            // 检查用户是否存在
            User curUser = this.checkUserIsExit(user);

            boolean check = false;

            //检查密码是否匹配
            if(curUser!=null) {
                check = checkPassword(user);
            }

            if(curUser!=null && check) {

                // 更新用户最后登录时间
                curUser.setLastLogin(new Date());
                userRepository.save(curUser);

                // 生成jwt
                Map<String, String> payload = new HashMap<>();
                payload.put("userId", curUser.getUserId().toString()); // 加入一些非敏感的用户信息
                payload.put("userName", curUser.getUsername());    // 加入一些非敏感的用户信息
                String jwt = JwtUtil.generateToken(payload);
                return ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE).body(jwt);

            } else return ResponseEntity.status(602).body("用户名或密码错误。");

        } else return ResponseEntity.status(603).body("用户名或密码不合法。");
    }

    /**
     * 改密业务
     * @return ServiceRes
     */
//    @Override
    public ResponseEntity<String> changePassWord(User user) {
        if(this.updatePassWord(user)) return ResponseEntity.ok().body("改密成功。");
        else return ResponseEntity.status(604).body("改密失败。");
    }

    /**
     * 非对称加密
     * @param text 明文
     * @return 密文
     */
    private String MD5Code(String text) {
        return DigestUtils.md5DigestAsHex(text.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * 修改密码方法
     * @param user 传入用户名和新密码
     * @return 改密成功返回 true 失败返回 false
     */
    private Boolean updatePassWord(User user) {
        // 密码非对称加密
        user.setPassword(this.MD5Code(user.getPassword()));
        // 更新密码
        return true;
        //-----------------------------------------真的要return true吗？？？------------------------------------------
    }

    /**
     * 检查用户是否存在【用户名密码相同】
     * @param user 用户类
     * @return 用户存在返回 用户对象 不存在返回 null
     */
    private User checkUserIsExit(User user) {
//        LambdaQueryWrapper<User> lqw = new LambdaQueryWrapper<>();
//        lqw.eq(User::getUsername, user.getUsername());
//        lqw.eq(User::getPassword, user.getPassword());
//        return userMapper.selectOne(lqw);
        Integer userId = user.getUserId();
        User u = null;
        Optional<User> op = userRepository.findById(userId);
        if(op.isPresent()) {
            u = op.get();
        }
        return u;
    }

    /**
     * 判断用户名是否唯一
     * @param user 用户类
     * @return 唯一返回 true 不唯一返回 false
     */
    private Boolean checkUserNameIsUnique(User user) {
//        LambdaQueryWrapper<User> lqw = new LambdaQueryWrapper<>();
//        lqw.eq(User::getUsername, user.getUsername());
//        List<User> userList = userMapper.selectList(lqw);
//        return userList.size() == 0;
        String name = user.getUsername();
        User u = userRepository.findByUsername(name);
        return u == null;
    }

    /**
     * 判断用户名密码是否合法
     * @param user 用户类
     * @return 满足 【英文字母、数字、下划线】 返回 true，否则返回 false
     */
    private Boolean checkUserNameAndPassword(User user) {
        String regex = "^[_a-z0-9A-Z]+$";
        return user.getUsername().matches(regex) && user.getPassword().matches(regex);
    }


    //检查密码是否正确
    private boolean checkPassword(User user) {
        User u = userRepository.findByUsername(user.getUsername());
        String password = u.getPassword();
        if(user.getPassword().equals(password)) {
            return true;
        } else {
          return false;
        }
    }





    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String from;
    /**
     * 发送邮件
     *
     * @param to      收件人邮箱
     * @param subject 邮件主题
     * @param content 邮件内容
     */
    public void sendMail(String to, String subject, String content) throws MessagingException {
        // 创建邮件消息
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom(from);
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(content, true);
        // 发送邮件
        mailSender.send(message);
    }
}


