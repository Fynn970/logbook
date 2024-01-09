package com.fynn.logbook.util

import android.content.SharedPreferences
import com.tencent.mmkv.MMKV

class MMKVUtil private constructor(): SharedPreferences, SharedPreferences.Editor{

    companion object {
        private var instance: MMKVUtil? = null;
        private const val DEFAULT_KEY = "KEY";
        private lateinit var kv:MMKV

        private fun getMMKVById(key: String = DEFAULT_KEY) {
            kv = MMKV.mmkvWithID(key)
        }

        @JvmOverloads
        fun getInstance(key: String = DEFAULT_KEY): MMKVUtil{
            if (instance == null){
                instance = MMKVUtil()
            }
            getMMKVById(key)
            return instance as MMKVUtil
        }
    }

    override fun getAll(): MutableMap<String, *> {
        val keys = kv.allKeys()
        val map = mutableMapOf<String, Any>()
        keys?.forEach {
            if (it.contains("@")) {
                val typeList = it.split("@")
                when (typeList[typeList.size - 1]) {
                    String::class.simpleName -> map[it] = getString(it, "") ?: ""
                    Int::class.simpleName -> map[it] = getInt(it, 0)
                    Long::class.simpleName -> map[it] = getLong(it, 0L)
                    Float::class.simpleName -> map[it] = getFloat(it, 0f)
                    Boolean::class.simpleName -> map[it] = getBoolean(it, false)
                }
            }
        }
        return map
    }

    override fun getString(key: String?, defValue: String?): String? {
        return kv.getString(key?.let { getTypeKey<String>(it) }, defValue)
    }

    override fun getStringSet(key: String?, defValues: MutableSet<String>?): MutableSet<String>? {
        return kv.getStringSet(key?.let { getTypeKey<MutableSet<String>>(it) }, defValues)
    }

    override fun getInt(key: String?, defValue: Int): Int {
        return kv.getInt(key?.let { getTypeKey<Int>(it) }, defValue)
    }

    override fun getLong(key: String?, defValue: Long): Long {
        return kv.getLong(key?.let { getTypeKey<Int>(it) }, defValue)
    }

    override fun getFloat(key: String?, defValue: Float): Float {
        return kv.getFloat(key?.let { getTypeKey<Int>(it) }, defValue)
    }

    override fun getBoolean(key: String?, defValue: Boolean): Boolean {
        return kv.getBoolean(key?.let { getTypeKey<Int>(it) }, defValue)
    }

    override fun contains(key: String?): Boolean {
        return kv.contains(key?.let { getRealKey(it)})
    }

    override fun edit(): SharedPreferences.Editor {
        return kv.edit()
    }

    override fun registerOnSharedPreferenceChangeListener(listener: SharedPreferences.OnSharedPreferenceChangeListener?) {
        kv.registerOnSharedPreferenceChangeListener(listener)
    }

    override fun unregisterOnSharedPreferenceChangeListener(listener: SharedPreferences.OnSharedPreferenceChangeListener?) {
        kv.unregisterOnSharedPreferenceChangeListener(listener)
    }

    private inline fun <reified T> getTypeKey(key: String):String{
        val type = "@" + T::class.simpleName
        return if (key.contains(type)){
            key
        }else{
            key + type
        }
    }

    override fun putString(key: String?, value: String?): SharedPreferences.Editor {
        return kv.putString(key?.let { getTypeKey<String>(it) }, value)
    }

    override fun putStringSet(key: String?, values: MutableSet<String>?): SharedPreferences.Editor {
        return kv.putStringSet(key?.let { getTypeKey<MutableSet<String>>(it) }, values)
    }

    override fun putInt(key: String?, value: Int): SharedPreferences.Editor {
        return kv.putInt(key?.let { getTypeKey<Int>(it) }, value)
    }

    override fun putLong(key: String?, value: Long): SharedPreferences.Editor {
        return kv.putLong(key?.let { getTypeKey<Long>(it) }, value)
    }

    override fun putFloat(key: String?, value: Float): SharedPreferences.Editor {
        return kv.putFloat(key?.let { getTypeKey<Float>(it) }, value)
    }

    override fun putBoolean(key: String?, value: Boolean): SharedPreferences.Editor {
        return kv.putBoolean(key?.let { getTypeKey<Boolean>(it) }, value)

    }

    override fun remove(key: String?): SharedPreferences.Editor {
        return kv.remove(key?.let { getRealKey(key)})

    }

    override fun clear(): SharedPreferences.Editor {
        return kv.clear()
    }

    override fun commit(): Boolean {
        return kv.commit()
    }

    override fun apply() {
        kv.apply()
    }

    private fun getRealKey(key: String):String{
        val typeKys = listOf(getTypeKey<String>(key),getTypeKey<Long>(key),getTypeKey<Float>(key),getTypeKey<Int>(key),getTypeKey<Boolean>(key), getTypeKey<MutableSet<String>>(key))
        typeKys.forEach {
            if (kv.containsKey(it)){
                return it
            }
        }
        return ""
    }
}