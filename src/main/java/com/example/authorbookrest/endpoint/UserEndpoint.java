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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("v1/users")
public class UserEndpoint {

    private final UserService userService;
    private final UserMapper userMapper;
    private final JwtTokenUtil jwtTokenUtil;
    private final PasswordEncoder passwordEncoder;


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

}
