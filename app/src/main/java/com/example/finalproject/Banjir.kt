package com.example.finalproject

data class Banjir(
    val result: Result,
    val statusCode: Int
) {
    data class Result(
        val arcs: List<List<List<Int>>>,
        val bbox: List<Double>,
        val objects: Objects,
        val transform: Transform,
        val type: String
    ) {
        data class Objects(
            val output: Output
        ) {
            data class Output(
                val geometries: List<Geometry>,
                val type: String
            ) {
                data class Geometry(
                    val arcs: List<List<Int>>,
                    val properties: Properties,
                    val type: String
                ) {
                    data class Properties(
                        val area_id: String,
                        val area_name: String,
                        val city_name: String,
                        val geom_id: String,
                        val last_updated: String,
                        val parent_name: String,
                        val state: Int
                    )
                }
            }
        }

        data class Transform(
            val scale: List<Double>,
            val translate: List<Double>
        )
    }
}