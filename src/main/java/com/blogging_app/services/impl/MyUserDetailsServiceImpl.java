package com.blogging_app.services.impl;

import com.blogging_app.entity.UsersPrincipal;
import com.blogging_app.exceptions.ResourceNotFoundException;
import com.blogging_app.payload.UserDto;
import com.blogging_app.repository.UserRepo;
import com.blogging_app.services.MyUserDetailsService;
import com.blogging_app.entity.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsServiceImpl implements MyUserDetailsService {

    @Autowired
    UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByEmail(username).orElseThrow(() -> new ResourceNotFoundException("User", username));
        return new UsersPrincipal(user);
    }
}
