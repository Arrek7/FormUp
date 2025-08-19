package com.comarch.szkolenia.FormUp.dao;

import com.comarch.szkolenia.FormUp.model.Trening;
import java.util.List;

public interface ITreningDAO {
    List<Trening> findByUserId(int userId);
}
