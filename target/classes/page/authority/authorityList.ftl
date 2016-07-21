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
                        <h3 class="box-title">角色列表</h3>
                    </div>
                    <div class="box-body">
                        <button class="btn btn-info btn-sm right"
                                onclick="javascript:location.href='/authority/authorityPreCreate'">
                            创建新角色
                        </button>
                    </div>
                    <!-- /.box-header -->
                    <div class="box-body table-responsive">
                        <table id="example1" class="table table-bordered table-striped">
                            <thead>
                            <tr>
                                <th>角色id</th>
                                <th>角色名称</th>
                                <th>更新</th>
                                <th>删除</th>
                            </tr>
                            </thead>
                            <tbody>
                                <#list authorityList as item>
                                <tr>
                                    <td>${item.id}</td>
                                    <td>${item.name}</td>
                                    <td>
                                        <button class="btn btn-info btn-sm right"
                                                onclick="javascript:location.href='/authority/authorityDetailForUpdate?id=${item.id}'">更新
                                        </button>
                                    </td>
                                    <td>
                                        <button class="btn btn-info btn-sm right"
                                                onclick="javascript:location.href='/authority/authorityDelete?id=${item.id}'">
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