def can_build(env, plat):
    return plat=="android"

def configure(env):
    if env['platform'] == 'android':
        env.android_add_dependency("implementation ('com.google.android.gms:play-services-ads:17.2.0') { exclude group: 'com.android.support' }")
        env.android_add_to_manifest("src/AndroidManifestChunk.xml")
        env.android_add_java_dir("src")