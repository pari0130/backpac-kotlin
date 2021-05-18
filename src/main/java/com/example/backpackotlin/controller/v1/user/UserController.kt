package com.example.backpackotlin.controller.v1.user

import com.example.backpackotlin.biz.comm.exception.BaseException
import com.example.backpackotlin.biz.comm.message.MulLangMessage
import com.example.backpackotlin.biz.comm.util.RedisUtil
import com.example.backpackotlin.biz.core.user.dto.UserDto.Signin
import com.example.backpackotlin.biz.core.user.service.UserService
import com.example.backpackotlin.config.security.JwtTokenProvider
import com.example.backpackotlin.controller.response.ResponseController
import io.swagger.annotations.*
import org.slf4j.LoggerFactory
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@Api(tags = ["01. 회원관리"])
@RestController
@RequestMapping("api/v1")
class UserController(private val lang: MulLangMessage, private val response: ResponseController, private val userService: UserService, private val passwordEncoder: PasswordEncoder, private val jwtTokenProvider: JwtTokenProvider, private val redisUtils: RedisUtil) {
    @ApiOperation(value = "로그인", notes = "이메일 회원 로그인을 한다.")
    @PostMapping(value = ["/users/signin"])
    fun signin(@ApiParam(value = "회원ID : 이메일", required = true) @RequestParam userId: String?,
               @ApiParam(value = "비밀번호", required = true) @RequestParam password: String?): Any {
        val user = userService.getUserByUid(userId)
        if (!passwordEncoder.matches(password, user.userPw)) {
            throw BaseException(lang.getMessage("user.loginFail.msg"))
        }
        return response.token(jwtTokenProvider.createToken(user.userNo.toString(), user.roles), "", "")
    }

    @ApiOperation(value = "가입", notes = "회원가입을 한다.")
    @PostMapping(value = ["/users/signup"])
    fun signup(userDto: @Valid Signin?): Any {
        return response.single(userService.insertUser(userDto), lang.getCode("user.authCreated.code"), lang.getMessage("user.authCreated.msg"))
    }

    @ApiImplicitParams(ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header"))
    @ApiOperation(value = "로그아웃", notes = "회원 로그아웃을 수행 한다.")
    @PostMapping(value = ["/users/signout"])
    fun signout(): Any {
        val authentication = SecurityContextHolder.getContext().authentication
        val userId = authentication.name
        val user = userService.getUserByUid(userId)
        redisUtils.delete("JWT_TOKEN::" + user.userNo)
        return response.success(lang.getCode("user.logoutSuccess.code"), lang.getMessage("user.logoutSuccess.msg"))
    }

    @ApiImplicitParams(ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header"))
    @ApiOperation(value = "회원 리스트 조회", notes = "모든 회원을 조회한다")
    @GetMapping("/users")
    fun getUsers(@ApiParam(value = "페이지 번호", required = true) @RequestParam(defaultValue = "0") page: Int,
                 @ApiParam(value = "페이지 로우 사이즈", required = true) @RequestParam(defaultValue = "10") size: Int): Any {
        val pageable: Pageable = PageRequest.of(page, size)
        return response.page(userService.selectUsers(pageable), "", "")
    }

    @ApiImplicitParams(ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header"))
    @ApiOperation(value = "회원 단건 조회", notes = "회원 번호로 정보를 조회한다")
    @GetMapping("/users/{userNo}")
    fun getUser(@ApiParam(value = "회원 번호", required = true) @PathVariable userNo: String?): Any {
        return response.single(userService.getUserByUserNo(userNo), "", "")
    }

    companion object {
        private val log = LoggerFactory.getLogger(UserController::class.java)
    }

}
