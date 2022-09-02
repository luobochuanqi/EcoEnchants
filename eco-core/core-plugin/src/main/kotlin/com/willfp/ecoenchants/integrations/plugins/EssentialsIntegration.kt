package com.willfp.ecoenchants.integrations.plugins

import com.earth2me.essentials.Enchantments
import com.willfp.ecoenchants.enchants.EcoEnchant
import com.willfp.ecoenchants.enchants.EcoEnchants
import com.willfp.ecoenchants.integrations.EnchantRegistrationIntegration
import org.bukkit.NamespacedKey
import org.bukkit.enchantments.Enchantment

@Suppress("UNCHECKED_CAST")
class EssentialsIntegration: EnchantRegistrationIntegration {
    override fun registerEnchants() {
        for (enchantment in EcoEnchants.values()) {
            // why aren't you using the api you PRd in
            // because essentials named mending to repairing etc
            Enchantments::class.java.getDeclaredField("ENCHANTMENTS")
                .apply {
                    isAccessible = true
                    (get(null) as MutableMap<String, Enchantment>).apply {
                        put(enchantment.id, enchantment)
                        put(enchantment.id.replace("_",""), enchantment)
                    }
                }

            Enchantments.registerEnchantment(enchantment.id, enchantment)
        }
    }

    override fun removeEnchant(enchantment: EcoEnchant) {
        Enchantments::class.java.getDeclaredField("ENCHANTMENTS")
            .apply {
                isAccessible = true
                (get(null) as MutableMap<String, Enchantment>).apply {
                    for (enchant in values.filterIsInstance<EcoEnchant>()) {
                        remove(enchantment.id)
                        remove(enchantment.id.replace("_",""))
                    }
                }
            }
    }

    override fun getPluginName() = "Essentials"
}
