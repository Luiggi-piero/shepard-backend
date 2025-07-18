package com.example.skilllinkbackend.shared.roledeletionhandler;

import com.example.skilllinkbackend.features.role.model.RolesEnum;

/**
 * Control de eliminación de registros por roles
 * - Revisa si el id del usuario se encuentra en alguna tabla de los roles: mentors, mentees, etc
 * - Si existe, hace una eliminación física
 */
public interface RoleDeletionHandler {
    void deleteIfExistsByUserId(Long id);

    RolesEnum supports();
}
