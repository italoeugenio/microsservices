package com.ms.user.model.repository;

import com.ms.user.model.entities.RefreshTokenModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

public interface RefreshTokenRepository extends JpaRepository<RefreshTokenModel, UUID> {
    Optional<RefreshTokenModel> findByToken(String token);

    @Modifying
    @Query("UPDATE RefreshToken SET revoked = true, revokeAt = :now WHERE user.email = :email AND revoked = false")
    void revokeAllByUserEmail(String email, LocalDateTime now);

    @Modifying
    @Query("UPDATE RefreshToken SET revoked = true, revokeAt = :now WHERE user.id = :userId AND revoked = false")
    void revokeAllByUserId(UUID userId, LocalDateTime now);
}
