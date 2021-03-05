package by.vasilevskiy.dota2analytics.helpers

class TeamsLogoHelper() {

    private val problemTeam = mapOf(
        "Team Secret" to "https://avatars.mds.yandex.net/get-ynews/1780398/d2540492eac4f712ccfdc17844a55cbb/orig",
        "INVICTUS GAMING" to "https://tx-free-imgs.acfun.cn/content/2019_07_09/1562655734578.JPG?imageslim",
        "LV-Gaming" to "https://www.progamer.ru/uploads/2014/09/lv-gaming.png",
        "Evil Geniuses" to "https://www.dailyesports.gg/wp-content/uploads/2018/10/eg-800x400.jpg",
        "Team Liquid" to "https://media-exp1.licdn.com/dms/image/C560BAQFg9mWn9a1-nQ/company-logo_200_200/0/1587423131496?e=2159024400&v=beta&t=Qru8oyUkPUX75SCjYzd2VM-UBiCCSk3GZwupOX7x9QY",
        "Thunder Predator" to "https://s.starladder.com/uploads/team_logo/7/3/6/3/thumb_270_39671c6debb1d304f6b7db719734ca29.png",
        "Speed Gaming International" to "https://yt3.ggpht.com/ytc/AAUvwnhOoJCD0Q2Wvv_SOhE0tLvnGRuv9gxK3cvr0zEFWQ=s900-c-k-c0x00ffffff-no-rj"
    )

    fun checkProblemTeam(teamName: String): Boolean {
        return problemTeam.containsKey(teamName)
    }

    fun getImageUrl(teamName: String): String {
        return problemTeam.getValue(teamName)
    }

}