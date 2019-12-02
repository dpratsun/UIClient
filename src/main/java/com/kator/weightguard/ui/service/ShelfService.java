package com.kator.weightguard.ui.service;

import com.kator.weightguard.ui.entity.Shelf;

import java.util.List;

/**
 * Created by prats on 3/8/18.
 */
public interface ShelfService {
    List<Shelf> getAll();
    Boolean resetShelf(String shelfId);
    List<Shelf> getShelfByControllerId(String controllerId);
    Boolean shelfUpdate(String shelfId, String shelfTitle);
}
