<#import "layouts/layout.ftl" as layout />
<@layout.mainLayout>
<aside class="right-side">
    <section class="content">
        <div class="col-md-9">
            <div class="box box-solid">
                <div class="box-header">
                    <i class="fa fa-text-width"></i>

                    <h3 class="box-title">服务器信息</h3>
                </div>
                <!-- /.box-header -->
                <div class="box-body">
                    <p class="lead">服务器名称：${item.name}(${item.id})</p>

                    <p class="text-light-blue">服务器ip地址：</p>

                    <p class="text-light-blue">服务器端口：</p>

                    <p class="text-light-blue">服务器预计开服时间：</p>

                    <p class="text-red">服务器状态：
                        <#switch item.status>
                            <#case 0>
                                等待开服
                                <#break>
                            <#case 1>
                                正常运行中
                                <#break>
                            <#case 2>
                                维护中
                                <#break>
                            <#case -1>
                                停服
                                <#break>
                        </#switch>
                    </p>

                </div>
                <!-- /.box-body -->
            </div>
            <!-- /.box -->
        </div>
        </div>
    </section>
</aside>
</@layout.mainLayout>

