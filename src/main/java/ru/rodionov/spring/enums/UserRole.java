package ru.rodionov.spring.enums;

public enum UserRole {
    ADMIN, // создает, удаляет, исправляет, получает всех свои записи
    MANAGER, // создает W, смотрит всех своих W и их записи (действия), удаляет сущности, редактирует
    WORKER  // создает Cl и действия (Cl редактирует, действия только создает новые)
}
// Action