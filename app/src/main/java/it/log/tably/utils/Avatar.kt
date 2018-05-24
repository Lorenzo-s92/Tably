package it.log.tably.utils

import it.log.tably.R

/**
 * Created by log on 17/01/18.
 * Temp class for handling avatars
 */


class Avatar {
    companion object {
        val map : Map<String, Int> = hashMapOf(
                "a.bellitto@reply.it" to R.drawable.a_bellitto,
                "a.risoli@reply.it" to R.drawable.a_risoli,
                "c.lidestri@reply.it" to R.drawable.c_lidestri,
                "d.tonella@reply.it" to R.drawable.d_tonella,
                "d.destefanis@reply.it" to R.drawable.d_destefanis,
                "e.garolla@reply.it" to R.drawable.e_garolla,
                "g.cirillo@reply.it" to R.drawable.g_cirillo,
                "k.benihich@reply.it" to R.drawable.k_benihich,
                "l.baldonero@reply.it" to R.drawable.l_baldonero,
                "l.giacosa@reply.it" to R.drawable.l_giacosa,
                "m.mastropasqua@reply.it" to R.drawable.m_mastropasqua,
                "n.mincuzzi@reply.it" to R.drawable.n_mincuzzi
           //     "l.simonigh@reply.it" to R.drawable.l_simonigh
        ).withDefault { R.drawable.empty_player }
    }
}