package com.priyo.nests.data.source.local.dao

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.RealmSet
import io.realm.annotations.PrimaryKey
import java.util.UUID

open class OptionDao() : RealmObject() {
    @PrimaryKey
    var id: String = ""
    var name: String = ""
    var icon: String = ""
}

open class FacilityDao() : RealmObject() {
    @PrimaryKey
    var facilityId: String = ""
    var name: String = ""
    var options: RealmList<OptionDao> = RealmList<OptionDao>()
}

open class ExclusionDao() : RealmObject() {

    var facilityId: String = ""
    var optionsId: String = ""
}

open class ExclusionSetDao() : RealmObject() {
    var exclusionSet: RealmSet<ExclusionDao> = RealmSet()
}

open class FiltersDao() : RealmObject() {
    @PrimaryKey
    var id = UUID.randomUUID().toString()
    var facilities: RealmList<FacilityDao> = RealmList()
    var exclusions: RealmList<ExclusionSetDao> = RealmList()
}
