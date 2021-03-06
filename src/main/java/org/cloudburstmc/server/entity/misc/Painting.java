package org.cloudburstmc.server.entity.misc;

import lombok.RequiredArgsConstructor;
import org.cloudburstmc.server.entity.Entity;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

import static com.google.common.base.Preconditions.checkNotNull;

public interface Painting extends Entity {

    Motive getMotive();

    void setMotive(@Nonnull Motive motive);

    @RequiredArgsConstructor
    enum Motive {
        ALBAN("Alban", 1, 1),
        AZTEC("Aztec", 1, 1),
        AZTEC2("Aztec2", 1, 1),
        BOMB("Bomb", 1, 1),
        KEBAB("Kebab", 1, 1),
        PLANT("Plant", 1, 1),
        WASTELAND("Wasteland", 1, 1),
        COURBET("Courbet", 2, 1),
        POOL("Pool", 2, 1),
        SEA("Sea", 2, 1),
        CREEBET("Creebet", 2, 1),
        SUNSET("Sunset", 2, 1),
        GRAHAM("Graham", 1, 2),
        WANDERER("Wanderer", 1, 2),
        BUST("Bust", 2, 2),
        MATCH("Match", 2, 2),
        SKULL_AND_ROSES("SkullAndRoses", 2, 2),
        STAGE("Stage", 2, 2),
        VOID("Void", 2, 2),
        WITHER("Wither", 2, 2),
        FIGHTERS("Fighters", 4, 2),
        DONKEY_KONG("DonkeyKong", 4, 3),
        SKELETON("Skeleton", 4, 3),
        BURNINGSKULL("BurningSkull", 4, 4);
        PIG_SCENE("Pigscene", 4, 4),
        POINTER("Pointer", 4, 4),

        public final String title;
        public final int width;
        public final int height;

        private static final Map<String, Motive> BY_NAME = new HashMap<>();

        static {
            for (Motive motive : values()) {
                BY_NAME.put(motive.title, motive);
            }
        }

        @Nullable
        public static Motive from(String name) {
            return from(name, null);
        }

        public static Motive from(String name, Motive defaultValue) {
            checkNotNull(name, "name");
            return BY_NAME.getOrDefault(name, defaultValue);
        }

        public int getHeight() {
            return height;
        }

        public int getWidth() {
            return width;
        }

        public String getTitle() {
            return title;
        }
    }
}
