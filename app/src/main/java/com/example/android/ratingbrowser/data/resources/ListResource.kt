package com.example.android.ratingbrowser.data.resources

import com.example.android.ratingbrowser.data.Queries
import com.example.android.ratingbrowser.data.db.AppDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope

abstract class ListResource<Entity>(
    queries: Queries,
    database: AppDatabase,
    scope: CoroutineScope = GlobalScope
) : Resource<List<Entity>, Unit>(queries, database, scope) {
    override suspend fun getFromDb(payload: Unit): List<Entity>? {
        val result = getFromDb()
        return if (result.isEmpty()) {
            null
        } else {
            result
        }
    }

    override suspend fun getFromNetwork(payload: Unit): List<Entity> = getFromNetwork()

    suspend fun get(): List<Entity> = get(Unit)

    abstract suspend fun getFromDb(): List<Entity>

    abstract suspend fun getFromNetwork(): List<Entity>
}