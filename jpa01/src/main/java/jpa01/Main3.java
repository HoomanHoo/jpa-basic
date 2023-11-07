package jpa01;

import org.slf4j.LoggerFactory;

import jakarta.persistence.EntityExistsException;
import jpa01.domain.User;
import jpa01.jpa04.ChangeNameService;
import jpa01.jpa04.GetUserService;
import jpa01.jpa04.NewUserService;
import jpa01.jpa04.NoUserException;
import jpa01.jpa04.RemoveUserService;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.time.LocalTime;

import org.slf4j.Logger;

public class Main3 {
    // private static Logger logger = LoggerFactory.getLogger(Main2.class);
    // private static NewUserService newUserService = new NewUserService();
    // private static GetUserService getUserService = new GetUserServiceㄴ();
    // private static ChangeNameService changeNameService = new ChangeNameService();
    // private static RemoveUserService removeUserService = new RemoveUserService();

    // private static void handleNew(String line) {
    // String[] value = line.substring(4).split(" ");
    // User user = new User(value[0], value[1], LocalDateTime.now());
    // try {
    // newUserService.asveNewUser(user);
    // logger.info("새 사용자 저장: {}", user);
    // } catch (EntityExistsException e) {
    // logger.info("사용자가 이미 존재함: {}", user.getEmail());
    // }
    // }

    // private static void handleGet(String line) {
    // String email = line.substring(4);
    // try {
    // User user = getUserService.getUser(email);
    // logger.info("사용자 정보: {}", user);
    // } catch (NoUserException e) {
    // logger.info("사용자가 존재하지 않음: {}", email);
    // }
    // }

    // private static void handleChangeName(String line) {
    // String[] v = line.substring(12).split(" ");
    // String email = v[0];
    // String newName = v[1];
    // try {
    // changeNameService.changeName(email, newName);
    // logger.info("사용자 이름 변경: {}, {}", email, newName);
    // } catch (NoUserException e) {
    // logger.info("사용자가 존재하지 않음: {}", email);
    // }
    // }

    // private static void handleRemove(String line) {
    // String email = line.substring(7);
    // try {
    // removeUserService.removeUser(email);
    // logger.info("사용자 삭제함: {}", email);
    // } catch (NoUserException e) {
    // logger.info("사용자가 존재하지 않음: {}", email);
    // }
    // }

    // public static void main(String[] args) {

    // ENF.init();
    // try (BufferedReader reader = new BufferedReader(new
    // InputStreamReader(System.in))) {
    // while (true) {
    // System.out.println("명령어를 입력하세요");
    // String line = reader.readLine();
    // if (line == null)
    // break;
    // if (line.startsWith("new ")) {
    // handleNew(line);
    // } else if (line.startsWith("get ")) {
    // handleGet(line);
    // } else if (line.startsWith("change name ")) {
    // handleChangeName(line);
    // } else if (line.startsWith("remove ")) {
    // handleRemove(line);
    // } else if (line.equals("exit")) {
    // break;
    // }
    // }
    // } catch (Exception e) {
    // e.printStackTrace();
    // } finally {
    // ENF.close();
    // }
    // }

}
