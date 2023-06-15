package com.alwan.plantdisease.util

import com.alwan.plantdisease.core.domain.entity.Disease

object Data {
    private val imageCornBlight = listOf(
        "https://drive.google.com/uc?id=1P-tc9wPzczeEWt6YjW6czUKOgDZpI03I",
        "https://drive.google.com/uc?id=1ORhd_400uk-EdB9sWyY7pOb-DCq05wzJ",
        "https://drive.google.com/uc?id=1WF2MC0UWH6luEO1txZNbbCvOQEtL5t7_",
        "https://drive.google.com/uc?id=1XUT2n-LRUFb78QCqqncUlsHDQX8VOZ4g",
        "https://drive.google.com/uc?id=1kCOBuL4Kdb2UFjlMGa9ETOV38-A97l3L",
    )

    private val imageCornCommonRust = listOf(
        "https://drive.google.com/uc?id=11xGFHnNIgRJ5XzVwZe_wj3JIA-Dol1J0",
        "https://drive.google.com/uc?id=1d04-l74tzg8TDV7Z-airrsJi58HRuzfG",
        "https://drive.google.com/uc?id=1EAYmxaDN54KYJy2f8FdcKtljCTXmlLbj",
        "https://drive.google.com/uc?id=1kwXOI_IgykhYjO4IUfpxyC9HoRdGydF4",
        "https://drive.google.com/uc?id=11rVQZ5nezLQuaABRgvFLJ0pzfTK06cLG",
    )

    private val imageCornGrayLeafSpot = listOf(
        "https://drive.google.com/uc?id=1fasLYxzvo9bdH2L7yT70ndRhUC_FJePn",
        "https://drive.google.com/uc?id=1YcGTQipddNMEmW88j4fMnSy3S-OgElQj",
        "https://drive.google.com/uc?id=1Te5yHQXG_VoXERRXF4R1OXCHvfi3fsHN",
        "https://drive.google.com/uc?id=1ZT6VEfhsOkYWfQpcZ-qnuhf9s72YOrEV",
        "https://drive.google.com/uc?id=1N-RuSwWCCduDLEQvsr6FuW30ezOzP9iN",
    )

    private val imagePotatoEarlyBlight = listOf(
        "https://drive.google.com/uc?id=1iQ6Ii2e2FBK0oUE2eIMeF4C2z7fV9wsX",
        "https://drive.google.com/uc?id=1DJo-TK1f0jVb2FZqz5tPO4fkDhWtQuiz",
        "https://drive.google.com/uc?id=1NyZvOyc6fvjUNKQUUPpzSlg41HURN3ud",
        "https://drive.google.com/uc?id=1BpPozFJu9S3WvTVer2n8-q74cp8JPVTh",
        "https://drive.google.com/uc?id=1trC0JKJUoVljF_ODWxMEa34GS5NllbHL",
    )

    private val imagePotatoLateBlight = listOf(
        "https://drive.google.com/uc?id=1zdfDVox2UTZwYTsMc0FWWXy-41rh2tbE",
        "https://drive.google.com/uc?id=1iK1gaPEHlCK0Z-n0SkqQ9k5JkXg9Upno",
        "https://drive.google.com/uc?id=1ja8-IriwdfpDekMniC24M10G2Nsqhxm5",
        "https://drive.google.com/uc?id=1vjNAMDyYJi7oPFWEOGEij3L3rJFF5Hf0",
        "https://drive.google.com/uc?id=1KyNT74oZEwO2rued3EOovUwbe_ZoM_V1",
    )

    fun getPlantDisease(): List<Disease> {
        val plantDisease = ArrayList<Disease>().apply {
            addAll(getPotatoDisease())
            addAll(getCornDisease())
        }

        return plantDisease
    }

    private fun getPotatoDisease() = listOf(
        Disease(
            id = "POTATO-EARLYBLIGHT",
            name = "Early Blight",
            type = "Kentang",
            imageUrl = "https://drive.google.com/uc?id=1PiKMigWai8uTlMpVe7P7Sc2pqdGB0M28",
            relatedImageUrl = imagePotatoEarlyBlight,
            cause = "Penyakit bercak kering disebabkan oleh jamur Alternaria solani. Jamur ini hidup dengan sisa tanaman sakit dan berkembang di daerah kering. Gejala yang ditimbulkan seperti daun berbercak kecil tersebar tidak teratur, warna coklat tua, meluas ke daun muda. Permukaan kulit umbi berbercak gelap tidak beraturan, kering, berkerut dan keras.",
            countermeasure = "Cara pengendalian layu bakteri pada tanaman kentang bisa dilakukan pergiliran tanaman. Sedang pencegahan bisa dilakukan dengan Natural Glio pada sebelum atau awal tanam.",
        ),
        Disease(
            id = "POTATO-LATEBLIGHT",
            name = "Late Blight",
            type = "Kentang",
            imageUrl = "https://drive.google.com/uc?id=1ja8-IriwdfpDekMniC24M10G2Nsqhxm5",
            relatedImageUrl = imagePotatoLateBlight,
            cause = "Penyakit busuk daun disebabkan oleh jamur Phytopthora infestans. Gejala yang ditimbulkan seperti timbul bercak-bercak kecil berwarna hijau kelabu dan agak basah hingga warnanya berubah menjadi coklat sampai hitam dengan bagian tepi berwarna putih yang merupakan sporangium dan daun membusuk/mati.",
            countermeasure = "Cara pengendalian busuk daun pada tanaman kentang bisa dilakukan dengan membuat sanitasi kebun. Sedang pencegahan bisa dilakukan dengan Natural Glio pada sebelum atau awal tanam.",
        ),
    )

    private fun getCornDisease() = listOf(
        Disease(
            id = "CORN-BLIGHT",
            name = "Blight",
            type = "Jagung",
            imageUrl = "https://drive.google.com/uc?id=1kCOBuL4Kdb2UFjlMGa9ETOV38-A97l3L",
            relatedImageUrl = imageCornBlight,
            cause = "Disebabkan oleh cendawan Helminthosporium turcicum.\n" +
                    "\n" +
                    "Pada daun tampak bercak memanjang dan teratur berwarna kuning dan dikelilingi warna coklat, bercak berkembang dan meluas dari ujung daun hingga ke pangkal daun, semula bercak tampak basah, kemudian berubah warna menjadi coklat kekuningan, kemudian berubah menjadi coklat tua.",
            countermeasure = "1. pergiliran tanaman.\n" +
                    "2. mengatur kondisi lahan tidak lembab.\n" +
                    "3. prefentif diawali dengan glio.\n" +
                    "4. penyakit karat (rust), cendawan puccina sorghi schw dan polypore.",
        ),
        Disease(
            id = "CORN-COMMONRUST",
            name = "Common Rust",
            type = "Jagung",
            imageUrl = "https://drive.google.com/uc?id=1EAYmxaDN54KYJy2f8FdcKtljCTXmlLbj",
            relatedImageUrl = imageCornCommonRust,
            cause = "Penyakit ini disebabkan oleh jamur Puccinia sorghi. \n" +
                    "\n" +
                    "Terdapat bercak kecil berbentuk oval kemudian bercak semakin memanjang warna hijau ke abu-abuan atau coklat.  Panjang hawr 2,5 – 15 cm, bercak mulai muncul pada daun yang terbawah kemudian berkembang menuju daun atas.",
            countermeasure = " Selalu pertimbangkan pendekatan terpadu dengan tindakan pencegahan bersama dengan perlakuan hayati jika tersedia. Penyemprotan fungisida dapat bermanfaat jika digunakan pada varietas yang rentan. Berikan fungisida daun pada awal musim jika karat menyebar dengan cepat karena kondisi cuaca. Banyak jenis fungisida yang tersedia untuk pengendalian karat. Produk-produk yang mengandung mankozeb, piraklostrobin, piraklostrobin + metkonazol, piraklostrobin + fluksapiroksad, azoksistrobin + propikonazol, trifloksistrobin + protiokonazol dapat digunakan untuk mengendalikan penyakit ini. Contoh perlakuannya dapat berupa penyemprotan mankozeb @ 2,5 g/l segera setelah bintil-bintil muncul dan ulangi dalam selang 10 hari hingga tahap berbunga.",
        ),
        Disease(
            id = "CORN-GRAYLEAFSPOT",
            name = "Gray Leaf Spot",
            type = "Jagung",
            imageUrl = "https://drive.google.com/uc?id=1N-RuSwWCCduDLEQvsr6FuW30ezOzP9iN",
            relatedImageUrl = imageCornGrayLeafSpot,
            cause = "Penyakit bercak daun abu-abu disebabkan oleh jamur Cercospora zeae-maydis.\n" +
                    "\n" +
                    "Nekrotik kecil (coklat atau sawo matang) yang bisa dikelilingi lingkaran kuning klorosis muncul di daun bagian bawah, biasanya sebelum berbunga. Secara bertahap luka ini akan berubah keabu-abuan dan muncul pada daun yang lebih muda juga. Ketika penyakit ini berkembang, luka ini membesar menjadi memanjang, bersegi empat yang sejajar dengan urat daun. Dalam kondisi optimal (suhu hangat, kelembaban tinggi dan daun basah), luka-luka dapat menyatu dan menutupi seluruh daun. Jika ini terjadi sebelum pembentukan biji, maka bisa terjadi kerugian panen yang cukup besar. Hawar daun dapat melemahkan tanaman dan kadang-kadang melunakkan batang, sehingga dapat membuatnya rubuh.",
            countermeasure = "Selalu pertimbangkan pendekatan terpadu berupa tindakan pencegahan dan perlakuan hayati jika tersedia. Perlakuan fungisida daun adalah cara untuk mengelola penyakit ini jika terjadi pada tahap awal tetapi harus mempertimbangkan kondisi cuaca, potensi kerugian panen dan kerentanan tanaman. Fungisida yang mengandung piraklostrobin dan strobilurin, atau kombinasi azoksistrobin dan propikonazol, protiokonazol dan trifloksistrobin bekerja dengan baik untuk mengendalikan jamur. ",
        ),
    )
}