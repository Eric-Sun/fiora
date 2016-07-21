<#import "layouts/layout.ftl" as layout />
<@layout.mainLayout>
<aside class="right-side">
    <!-- Content Header (Page header) -->

    <!-- Main content -->
    <section class="content">
        <div class="row">
            <div class="col-xs-12">

                <div class="box">
                    <div class="box-header">
                        <h3 class="box-title">选择服务器列表</h3>
                    </div>
                    <!-- /.box-header -->
                    <div class="box-body table-responsive">
                        <table id="example1" class="table table-bordered table-striped">
                            <thead>
                            <tr>
                                <th>服务器id</th>
                                <th>服务器名称</th>
                                <th>服务器ip地址</th>
                                <th>服务端口</th>
                                <th>当前状态</th>
                                <th>开始连接</th>
                            </tr>
                            </thead>
                            <tbody>
                                <#list gameServerList as item>
                                <tr>
                                    <td>${item.id}</td>
                                    <td>${item.name}</td>
                                    <td>${item.ip}</td>
                                    <td>${item.port?number}</td>
                                    <td>
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
                                    </td>

                                    <td>
                                        <button class="btn btn-info btn-sm right"
                                                onclick="javascript:location.href='/gameserver/setCurrent?id=${item.id}'">选择此服务器
                                        </button>
                                    </td>
                                </tr>
                                </#list>
                            </tbody>
                        </table>
                    </div>
                    <!-- /.box-body -->

                </div>
                <!-- /.box -->
            </div>
        </div>

    </section>
    <!-- /.content -->
</aside>
</@layout.mainLayout>