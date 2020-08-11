package com.instagram.service;

import com.instagram.dto.DtoChat;

import java.util.List;

public interface ServiceChat {


    List<DtoChat> getDtoChatsByUsername(String username);


}
