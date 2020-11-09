package com.example.tmapi.service;

import com.example.tmapi.entity.Items;

import java.util.List;
import java.util.Map;

public interface ItemsService {
    Map<String,Object> queryByDate(Items items);
}
