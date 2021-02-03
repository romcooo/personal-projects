package com.romco.bracketeer.security

import com.romco.bracketeer.domain.User
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.util.stream.Collector
import java.util.stream.Collectors
import java.util.stream.Stream

class User2UserDetailsAdapter {

    fun getUser(userDetails: UserDetails) {

    }

    fun getUserDetails(user: User) {

    }

}

class CustomUserDetails(private var authorities: MutableCollection<out GrantedAuthority>,
                        private val password: String,
                        private val username: String,
                        private var isAccountNonExpired: Boolean = true,
                        private var isAccountNonLocked: Boolean = true,
                        private var isCredentialsNonExpired: Boolean = true,
                        private var isEnabled: Boolean = true) : UserDetails {


    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return authorities
    }

    override fun getPassword(): String {
        return password
        TODO("https://reflectoring.io/spring-security-password-handling/")
    }

    override fun getUsername(): String {
        return username
    }

    override fun isAccountNonExpired(): Boolean {
        return isAccountNonExpired
    }

    override fun isAccountNonLocked(): Boolean {
        return isAccountNonLocked
    }

    override fun isCredentialsNonExpired(): Boolean {
        return isCredentialsNonExpired
    }

    override fun isEnabled(): Boolean {
        return isEnabled
    }

    companion object {
        fun buildFromUser(user: User): CustomUserDetails {
            val username = user.username
            val password = user.passwordHash
            val authorities: MutableCollection<CustomGrantedAuthority>? = user.roles.stream()
                    /* map -> take all roles
                     -> stream
                     ->

                     */
                    .flatMap { roles -> roles.privileges.stream() }
                    ?.map { privilege -> CustomGrantedAuthority(privilege.name) }
                    ?.collect(Collectors.toList())

// TODO check https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.sequences/


            val isAccountNonExpired = true
            val isAccountNonLocked = true
            val isCredentialsNonExpired = true
            val isEnabled = true

            return CustomUserDetails(username = username,
                    password = password,
                    authorities = authorities!!,
                    isAccountNonExpired = isAccountNonExpired,
                    isAccountNonLocked = isAccountNonLocked,
                    isCredentialsNonExpired = isCredentialsNonExpired,
                    isEnabled = isEnabled)

        }
    }
}


class CustomGrantedAuthority(private val authority: String) : GrantedAuthority {
    override fun getAuthority(): String {
        return authority
    }
}