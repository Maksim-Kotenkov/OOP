package groovyscripts


import groovy.transform.Field


@Field
tasks = [
        "Task_1_1_1",
        "Task_1_1_2",
        "Task_1_2_1",
        "Task_1_2_2",
//            "Task_1_3_1",  // "death": implement skipping tests after timeout
        "Task_1_4_1",
        "Task_1_5_1",
        "Task_1_5_2"
]

@Field
groups = [
        22213: [
                "Maksim-Kotenkov": [
                        meta: "Kotenkov Maksim Matveevich",
                        repo: "https://github.com/Maksim-Kotenkov/OOP"
                ],
                "melarozz": [
                        meta: "Yakovleva Valeriya who",
                        repo: "https://github.com/melarozz/OOP"
                ],
                "TheBlek": [
                        meta: "Kuklin Georgy who",
                        repo: "https://github.com/TheBlek/OOP"
                ]
        ],
//        22215: [
//                "KozlovKV": [
//                        meta: "Kozlov Kirill who",
//                        repo: "https://github.com/KozlovKV/OOP"
//                ]
//        ],
//        22216: [
//                "McAninKirill": [
//                        meta: "Makanin Kirill who",
//                        repo: "https://github.com/McAninKirill/OOP"
//                ]
//        ]
]
