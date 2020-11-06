package com.example.tmapi.service;

import com.example.tmapi.entity.Items;

import java.util.List;

public interface ItemsService {
    List<Items> queryByDate(Items items);
}
