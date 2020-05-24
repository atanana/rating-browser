package com.atanana.datasource.resources

import com.atanana.datasource.Queries
import com.atanana.datasource.database.AppDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.launch

abstract class Resource<Data : Any, Payload>(
    protected val queries: Queries,
    protected val database: AppDatabase,
    private val scope: CoroutineScope = GlobalScope
) {
    protected abstract fun getFromDb(payload: Payload): Flow<Data?>

    protected abstract suspend fun saveToDb(data: Data)

    protected abstract suspend fun getFromNetwork(payload: Payload): Data

    private suspend fun update(payload: Payload) {
        val data = getFromNetwork(payload)
        saveToDb(data)
    }

    fun get(payload: Payload): Flow<Data> {
        scope.launch {
            update(payload)
        }
        return getFromDb(payload).mapNotNull { it }
    }
}