package com.gi.hrm.command.domain;

import java.time.LocalDateTime;

public sealed interface EntityState permits
        EntityState.Created,
        EntityState.Dirty,
        EntityState.Deleted,
        EntityState.Persisted {

    EntityState update();

    EntityState delete();

    EntityState persist();

    LocalDateTime createdAt();

    LocalDateTime updatedAt();

    /**
     * エンティティが新しく作成された状態
     *
     * @param createdAt
     * @param updatedAt
     */
    record Created(
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ) implements EntityState {
        @Override
        public EntityState update() {
            return new Created(createdAt, updatedAt);
        }

        @Override
        public EntityState delete() {
            return new Deleted(createdAt, updatedAt);
        }

        @Override
        public EntityState persist() {
            return new Persisted(createdAt, updatedAt);
        }
    }

    /**
     * 永続化されたエンティティが変更された状態
     *
     * @param createdAt
     * @param updatedAt
     * @param originalUpdatedAt
     */
    record Dirty(
            LocalDateTime createdAt,
            LocalDateTime updatedAt,
            LocalDateTime originalUpdatedAt
    ) implements EntityState {
        @Override
        public EntityState update() {
            return new Dirty(createdAt, LocalDateTime.now(), originalUpdatedAt);
        }

        @Override
        public EntityState delete() {
            return new Deleted(createdAt, updatedAt);
        }

        @Override
        public EntityState persist() {
            return new Persisted(createdAt, updatedAt);
        }
    }

    /**
     * 永続化されたエンティティが削除された状態
     *
     * @param createdAt
     * @param updatedAt
     */
    record Deleted(
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ) implements EntityState {
        @Override
        public EntityState update() {
            throw new IllegalStateException("Already deleted");
        }

        @Override
        public EntityState persist() {
            throw new IllegalStateException("Already deleted");
        }

        @Override
        public EntityState delete() {
            throw new IllegalStateException("Already deleted");
        }
    }


    /**
     * エンティティが永続化された状態
     *
     * @param createdAt
     * @param updatedAt
     */
    record Persisted(LocalDateTime createdAt, LocalDateTime updatedAt) implements EntityState {
        @Override
        public EntityState update() {
            return new Dirty(createdAt, LocalDateTime.now(), updatedAt);
        }

        @Override
        public EntityState delete() {
            return new Deleted(createdAt, updatedAt);
        }

        @Override
        public EntityState persist() {
            throw new IllegalStateException("Already persisted");
        }
    }

    static EntityState create() {
        return new Created(LocalDateTime.now(), LocalDateTime.now());
    }
}
