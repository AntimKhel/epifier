package com.example.epifier.repository.local

import com.example.epifier.repository.model.Detail
import javax.inject.Inject

class DetailRepository @Inject constructor(private val detailDao: DetailDao) {

    fun saveDetail(detail: Detail) {
        detailDao.saveDetail(detail)
    }
}

