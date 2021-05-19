package com.diets.dietplans.common.db.migrations

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

object Migrations {

    val migration_1_2 = object : Migration(1,2){
        override fun migrate(database: SupportSQLiteDatabase) {
            database.execSQL("create table FavoriteEntity (id int (10) NOT NULL, PRIMARY KEY (id));")
        }
    }
}