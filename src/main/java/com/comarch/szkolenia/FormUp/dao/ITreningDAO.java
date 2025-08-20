package com.comarch.szkolenia.FormUp.dao;

import com.comarch.szkolenia.FormUp.model.Trening;
import java.util.List;
import java.util.Optional;

public interface ITreningDAO {
    List<Trening> findByUserId(int userId);
    Optional<Trening> save(Trening trening);
}
