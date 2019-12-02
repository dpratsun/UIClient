package com.kator.weightguard.ui.service;

import com.kator.weightguard.ui.entity.ShelfError;

import java.io.IOException;
import java.util.List;

/**
 * Created by prats on 3/14/18.
 */
public interface ShelfErrorService {
    List<ShelfError> getAll();
    Boolean setChecked(Integer shelfErrorId);
}
