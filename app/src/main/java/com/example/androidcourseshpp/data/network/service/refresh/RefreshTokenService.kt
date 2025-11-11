package com.example.androidcourseshpp.data.network.service.refresh

import com.example.androidcourseshpp.data.network.service.refresh.entity.RefreshTokenEntity

interface RefreshTokenService {

    suspend fun refreshTokens(refreshTokenEntity: RefreshTokenEntity)
}