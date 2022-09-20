package com.alwan.plantdisease.util

import com.alwan.plantdisease.domain.entity.Disease

object DummyData {
    private const val N = 10
    private val imageUrls = listOf(
        "http://bbpadi.litbang.pertanian.go.id/media/k2/items/cache/3bd383cdf9446912a35458166e99234d_XL.jpg",
        "https://pertanian.kulonprogokab.go.id/files/news/normal/f41f8d59f7a28b79d8f83a3be0e24789.jpg",
        "https://i2.wp.com/gdmorganic.com/wp-content/uploads/2021/05/hama-dan-penyakit-padi.jpg",
        "https://www.99.co/blog/indonesia/wp-content/uploads/2022/04/penyakit-hawar-daun-bakteri.jpg",
        "https://www.99.co/blog/indonesia/wp-content/uploads/2022/04/hama-dan-penyakit-tanaman-padi.jpg"
    )

    fun getDummiesDisease(): List<Disease> {
        val diseases = ArrayList<Disease>()

        for (i in 0..N) {
            val disease = Disease(
                id = i.toString(),
                name = "Tanaman$i",
                type = "Tipe Tanaman$i",
                imageUrl = getRandomImageUrl(),
                relatedImageUrl = getAllImageUrl(),
                cause = "Caused by Lorem ipsum dolor sit amet$i, consectetur adipiscing elit.",
                countermeasure = "Countermeasures by  Ut enim ad minim eniam$i, quis nostrud ullamco. "
            )
            diseases.add(disease)
        }

        return diseases
    }

    private fun getRandomImageUrl(): String = imageUrls.random()

    private fun getAllImageUrl(): List<String> = imageUrls

}