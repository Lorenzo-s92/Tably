package it.log.tably.utils

import it.log.tably.R

/**
 * Created by log on 17/01/18.
 * Temp class for handling avatars
 */


class Avatar {
    companion object {
        val map : Map<String, Int> = hashMapOf(
                "a.bellitto" to R.drawable.a_bellitto,
                "a.risoli" to R.drawable.a_risoli,
                "c.lidestri" to R.drawable.c_lidestri,
                "d.tonella" to R.drawable.d_tonella,
                "d.destefanis" to R.drawable.d_destefanis,
                "e.garolla" to R.drawable.e_garolla,
                "g.cirillo" to R.drawable.g_cirillo,
                "k.benihich" to R.drawable.k_benihich,
                "l.baldonero" to R.drawable.l_baldonero,
                "Ninjiacosa" to R.drawable.l_giacosa,
                "m.mastropasqua" to R.drawable.m_mastropasqua,
                "n.mincuzzi" to R.drawable.n_mincuzzi
        ).withDefault { R.drawable.empty_player }
    }
}