
安装：

    mount -o loop afcAI_1_0_1.iso /mnt
    cd /mnt
    ./install.sh

    输入用户名，或使用默认用户名（afcAI）

    umount /mnt

配置：

    su afcAI
    cd ~

    vi afcAIAnalysis_env/default.xml

    修改db2和pgsql的配置并保存

启动：

    ./mgr.py start


添加crontab配置：

    vi /etc/crontab

    添加配置（以afcAI为例子）：

    0 2 * * * afcAI bash /home/afcAI/processGuarder/start.sh



