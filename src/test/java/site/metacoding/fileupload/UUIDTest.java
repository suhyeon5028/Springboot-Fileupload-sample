package site.metacoding.fileupload;

import java.util.UUID;

import org.junit.jupiter.api.Test;

public class UUIDTest { // 네트워크상 고유식별자

    @Test
    public void 유유아이디_연습() {
        UUID uuid = UUID.randomUUID();
        System.out.println(uuid.toString());
    }
}
