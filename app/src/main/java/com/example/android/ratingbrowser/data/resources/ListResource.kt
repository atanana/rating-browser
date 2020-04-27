package com.example.android.ratingbrowser.data.resources

import com.example.android.ratingbrowser.data.Queries
import com.example.android.ratingbrowser.data.db.AppDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

abstract class ListResource<Entity>(
    queries: Queries,
    database: AppDatabase,
    scope: CoroutineScope = GlobalScope
) : Resource<List<Entity>, Unit>(queries, database, scope) {
    override fun getFromDb(payload: Unit): Flow<List<Entity>?> =
        getFromDb().map {
            if (it.isEmpty()) {
                null
            } else {
                it
            }
        }

    override suspend fun getFromNetwork(payload: Unit): List<Entity> = getFromNetwork()

    fun get(): Flow<List<Entity>> = get(Unit)

    abstract fun getFromDb(): Flow<List<Entity>>

    abstract suspend fun getFromNetwork(): List<Entity>
}