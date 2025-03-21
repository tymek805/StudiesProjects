<?php
/**
 * Plugin Name: RandomAdsPlugin
 * Plugin URI: https://example.com/plugins/Newly Added Post Highliter/
 * Description: Displays random add at the begining of each post.
 * Version: 1.0
 * Requires at least: 5.0
 * Requires PHP: 7.2
 * Author: Mateusz Dejewski, Tymoteusz Lango
 * Author URI: https://github.com/MateuszDejewski/AdvancedWebTechnologies/tree/main/lista2%20(wordpress%20plugin)
 * License: GPL v2 or later
 * License URI: https://www.gnu.org/licenses/gpl-2.0.html
 */

if (!defined('ABSPATH')) {
    exit; // Exit if accessed directly
}

class Post_Ads_Plugin {
    public function __construct() {
        add_action('admin_menu', [$this, 'create_admin_page']);
        add_action('admin_init', [$this, 'register_settings']);
        add_filter('the_content', [$this, 'display_random_ad']);
        add_action('wp_enqueue_scripts', [$this, 'enqueue_styles']);
    }

    public function create_admin_page() {
        add_options_page('Ads', 'Ads in posts', 'manage_options', 'post-ads-plugin', [$this, 'admin_page_html']);
    }

    public function register_settings() {
        register_setting('post_ads_options_group', 'post_ads_content');
    }

    public function admin_page_html() {
        ?>
        <div class="wrap">
            <h1>Ads settings</h1>
            <h2>Split each add with "--end--".</p>
            <form method="post" action="options.php">
                <?php
                settings_fields('post_ads_options_group');
                do_settings_sections('post_ads_options_group');
                wp_editor(get_option('post_ads_content', ''), 'post_ads_content', ['textarea_name' => 'post_ads_content', 'media_buttons' => true, 'textarea_rows' => 10]);
                ?>  
                <?php submit_button(); ?>
            </form>
        </div>
        <?php
    }

    public function display_random_ad($content) {
        if (is_single()) {
            $ads = explode("--end--", get_option('post_ads_content', ''));
            if (!empty($ads)) {
                $random_ad = wp_kses_post($ads[array_rand($ads)]);
                $content = "<div class='post-ad'>" . $random_ad . "</div>" . $content;
            }
        }
        return $content;
    }

    public function enqueue_styles() {
        wp_enqueue_style('post-ads-style', plugin_dir_url(__FILE__) . '/css/style.css');
    }
}

new Post_Ads_Plugin();