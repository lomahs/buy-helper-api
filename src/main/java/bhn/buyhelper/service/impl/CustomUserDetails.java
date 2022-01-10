// package bhn.buyhelper.service.impl;

// import java.util.Arrays;
// import java.util.Collection;

// import org.springframework.security.core.GrantedAuthority;
// import org.springframework.security.core.authority.SimpleGrantedAuthority;
// import org.springframework.security.core.userdetails.UserDetails;

// import bhn.buyhelper.model.entity.Account;
// import bhn.buyhelper.model.entity.Role;
// import lombok.AllArgsConstructor;
// import lombok.Builder;
// import lombok.Data;

// @Data
// @AllArgsConstructor
// @Builder
// public class CustomUserDetails implements UserDetails {

// Account account;

// @Override
// public Collection<? extends GrantedAuthority> getAuthorities() {
// // TODO Auto-generated method stub
// return Arrays.asList(
// new SimpleGrantedAuthority(Role.BUYER.toString()),
// new SimpleGrantedAuthority(Role.PROVIDER.toString()),
// new SimpleGrantedAuthority(Role.TOWN_LEADER.toString()),
// new SimpleGrantedAuthority(Role.ADMIN.toString()));

// }

// @Override
// public String getPassword() {
// // TODO Auto-generated method stub
// return account.getPassword();
// }

// @Override
// public String getUsername() {
// // TODO Auto-generated method stub
// return account.getUsername();
// }

// @Override
// public boolean isAccountNonExpired() {
// // TODO Auto-generated method stub
// return false;
// }

// @Override
// public boolean isAccountNonLocked() {
// // TODO Auto-generated method stub
// return false;
// }

// @Override
// public boolean isCredentialsNonExpired() {
// // TODO Auto-generated method stub
// return false;
// }

// @Override
// public boolean isEnabled() {
// // TODO Auto-generated method stub
// return false;
// }

// }
