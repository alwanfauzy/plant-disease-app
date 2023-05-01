package com.alwan.plantdisease.util

import com.alwan.plantdisease.core.domain.entity.Disease

object DummyData {
    private val imageUrls = listOf(
        "http://bbpadi.litbang.pertanian.go.id/media/k2/items/cache/3bd383cdf9446912a35458166e99234d_XL.jpg",
        "https://pertanian.kulonprogokab.go.id/files/news/normal/f41f8d59f7a28b79d8f83a3be0e24789.jpg",
        "https://i2.wp.com/gdmorganic.com/wp-content/uploads/2021/05/hama-dan-penyakit-padi.jpg",
        "https://www.99.co/blog/indonesia/wp-content/uploads/2022/04/penyakit-hawar-daun-bakteri.jpg",
        "https://www.99.co/blog/indonesia/wp-content/uploads/2022/04/hama-dan-penyakit-tanaman-padi.jpg"
    )

    fun getDummiesDisease() = listOf(
        Disease(
            id = "1",
            name = "Early Blight",
            type = "Kentang",
            imageUrl = "https://drive.google.com/uc?id=1PiKMigWai8uTlMpVe7P7Sc2pqdGB0M28",
            relatedImageUrl = getAllImageUrl(),
            cause = "Penyakit bercak kering disebabkan oleh jamur Alternaria solani. Jamur ini hidup dengan sisa tanaman sakit dan berkembang di daerah kering. Gejala yang ditimbulkan seperti daun berbercak kecil tersebar tidak teratur, warna coklat tua, meluas ke daun muda. Permukaan kulit umbi berbercak gelap tidak beraturan, kering, berkerut dan keras.",
            countermeasure = "Cara pengendalian layu bakteri pada tanaman kentang bisa dilakukan pergiliran tanaman. Sedang pencegahan bisa dilakukan dengan Natural Glio pada sebelum atau awal tanam.",
        ),
        Disease(
            id = "2",
            name = "Late Blight",
            type = "Kentang",
            imageUrl = "https://drive.google.com/uc?id=1S13w6zpSEUF9W1JbNx7sDMPdaAtxWLBc",
            relatedImageUrl = getAllImageUrl(),
            cause = "Penyakit busuk daun disebabkan oleh jamur Phytopthora infestans. Gejala yang ditimbulkan seperti timbul bercak-bercak kecil berwarna hijau kelabu dan agak basah hingga warnanya berubah menjadi coklat sampai hitam dengan bagian tepi berwarna putih yang merupakan sporangium dan daun membusuk/mati.",
            countermeasure = "Cara pengendalian busuk daun pada tanaman kentang bisa dilakukan dengan membuat sanitasi kebun. Sedang pencegahan bisa dilakukan dengan Natural Glio pada sebelum atau awal tanam.",
        ),
    )

    private fun getAllImageUrl(): List<String> = imageUrls

}