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
                        <h3 class="box-title">资源列表</h3>
                    </div>
                    <div class="box-body">
                        <button class="btn btn-info btn-sm right"
                                onclick="javascript:location.href='/authority/resourcePreCreate'">
                            创建新角色
                        </button>
                    </div>
                    <!-- /.box-header -->
                    <div class="box-body table-responsive">
                        <table id="example1" class="table table-bordered table-striped">
                            <thead>
                            <tr>
                                <th>资源id</th>
                                <th>资源名称</th>
                                <th>更新</th>
                                <th>删除</th>
                            </tr>
                            </thead>
                            <tbody>
                                <#list resourceList as item>
                                <tr>
                                    <td>${item.id}</td>
                                    <td>${item.name}</td>
                                    <td>
                                        <button class="btn btn-info btn-sm right"
                                                onclick="javascript:location.href='/authority/resourceDetailForUpdate?id=${item.id}'">
                                            更新
                                        </button>
                                    </td>
                                    <td>
                                        <button class="btn btn-info btn-sm right"
                                                onclick="javascript:location.href='/authority/resourceDelete?id=${item.id}'">
                                            删除
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