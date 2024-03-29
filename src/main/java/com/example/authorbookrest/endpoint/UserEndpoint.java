package com.example.authorbookrest.endpoint;

import com.example.authorbookrest.dto.AuthRequestDto;
import com.example.authorbookrest.dto.AuthResponseDto;
import com.example.authorbookrest.dto.SaveUserDto;
import com.example.authorbookrest.dto.UserResponseDto;
import com.example.authorbookrest.entity.User;
import com.example.authorbookrest.mapper.UserMapper;
import com.example.authorbookrest.service.UserService;
import com.example.authorbookrest.util.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("v1/users")
public class UserEndpoint {

    private final UserService userService;
    private final UserMapper userMapper;
    private final JwtTokenUtil jwtTokenUtil;
    private final PasswordEncoder passwordEncoder;

    @Value("${upload.image.path}")
    private String uploadImagePath;


    @PostMapping
    public ResponseEntity<SaveUserDto> register(@RequestBody UserResponseDto userResponseDto) {
        User byEmail = userService.findByEmail(userResponseDto.getEmail());
        if (byEmail != null) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .build();
        }
        return ResponseEntity.ok(userService.create(userResponseDto));
    }

    @PostMapping("/image/{id}")
    public ResponseEntity<SaveUserDto> uploadImage(@PathVariable("id") int userId, @RequestParam("picName") MultipartFile multipartFile) throws IOException {
        User byId = userService.findById(userId);
        if (byId == null) {
            return ResponseEntity.notFound().build();
        }
        userService.uploadImage(byId, multipartFile);
        return ResponseEntity.ok(userMapper.map(byId));
    }

    @PostMapping("/auth")
    public ResponseEntity<AuthResponseDto> login(@RequestBody AuthRequestDto authRequestDto) {
        User user = userService.findByEmail(authRequestDto.getEmail());
        if (user == null || !passwordEncoder.matches(authRequestDto.getPassword(), user.getPassword())) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .build();
        }
        return ResponseEntity.ok(AuthResponseDto.builder()
                .token(jwtTokenUtil.generateToken(user.getEmail()))
                .saveUserDto(userMapper.map(user))
                .build());

    }

    @GetMapping(value = "/getImage", produces = MediaType.IMAGE_JPEG_VALUE)
    public @ResponseBody byte[] getImage(@RequestParam("picName") String picture) throws IOException {
        File file = new File(uploadImagePath, picture);
        if (file.exists()) {
            return IOUtils.toByteArray(new FileInputStream(file));
        }
        return null;
    }

}
