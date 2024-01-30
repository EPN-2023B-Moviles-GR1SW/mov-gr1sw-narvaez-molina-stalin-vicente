package com.example.b2023_gr1sw_svnm

class ICities(
    public var name: String?,
    public var state: String?,
    public var country: String?,
    public var capital: Boolean?,
    public var population: Long?,
    public var regions: List<String>?
) {
    override fun toString(): String {
        return "ICities(name=$name \nstate=$state \ncountry=$country,\n capital=$capital\npopulation=$population \nregions=$regions)"
    }
}