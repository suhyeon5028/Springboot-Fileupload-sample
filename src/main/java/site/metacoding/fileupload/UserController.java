package site.metacoding.fileupload;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class UserController {

    private final UserRepository userRepository;

    @GetMapping("/joinForm")
    public String joinForm() {
        return "joinForm";
    }

    @GetMapping("/main")
    public String main(Model model) {
        User user = userRepository.findById(1).get();

        model.addAttribute("user", user);
        return "main";
    }

    @PostMapping("/join")
    public String join(JoinDto joinDto) { // 버퍼로 읽는거 1. json 2. 있는 그대로 받고 싶을때

        UUID uuid = UUID.randomUUID(); // 범용 고유식별자

        String requestFileName = joinDto.getFile().getOriginalFilename();
        System.out.println("전송받은 파일명 : " + requestFileName);

        // 85a96d96-5022-4ee5-b9be-cf171289fd63_a.png
        String imgurl = uuid + "_" + requestFileName;
        // 메모리에 있는 파일 데이터를 파일시스템으로 옮겨야함.
        // 1. 빈 파일 생성 haha.png
        // File file = new File("d:\\example\\file.txt");
        // 2. 빈 파일에 스트림 연결
        // 3. for문 돌리면서 바이트로 쓰면 됨. FileWriter 객체!

        try {
            // 1. 폴더가 이미 만들어져 있어야 함.
            // 2. 경로 입력시 리눅스는 / 사용, 윈도우는 \ 사용 OS관점!
            // imgUrl = C:/upload/a.png
            // 3. 윈도우 : C:/upload/ 4. 리눅스 : /upload/
            // 우리는 상대경로 사용할 예정

            // jar 파일로 구우면 안돌아감
            Path filePath = Paths.get("src/main/resources/static/upload/" + imgurl);
            Files.write(filePath, joinDto.getFile().getBytes());

            userRepository.save(joinDto.toEntity(imgurl));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "joinComplete"; // ViewResolver
    }
}
