<#import "layouts/layout.ftl" as layout />
<@layout.mainLayout>
<aside class="right-side">
    <section class="content">
        <div class="col-md-12">
            <div class="box box-primary">
                <div class="box-body">
                    <form action="/account/query" method="post">
                        <div class="input-group margin">
                            <div class="form-group">
                                <label>账号id</label>
                                <input type="text" class="form-control" name="aid" placeholder="账号id"
                                        value="${RequestParameters["aid"]}"/>
                            </div>
                            <div class="form-group">
                                <label>账号名称</label>
                                <input type="text" class="form-control" name="name" placeholder="账号名称"
                                       value="${RequestParameters["name"]}"/>
                            </div>
                            <div class="form-group">
                                <button type="submit" class="form-control btn btn-primary"
                                        onclick="this.parentNode.submit(); return false;">查询
                                </button>
                            </div>
                        </div>
                </div>
                </form>
                <!-- /input-group -->
                <div class="box">
                    <div class="box-header">
                        <h3 class="box-title">账号信息</h3>
                    </div>
                    <!-- /.box-header -->
                    <div class="box-body table-responsive">
                        <table id="example1" class="table table-bordered table-striped">
                            <thead>
                            <tr>
                                <th>账号id</th>
                                <th>账号名称</th>
                                <th>账号状态</th>
                                <th>操作</th>
                            </tr>
                            </thead>
                            <tbody>
                                <tr>
                                    <td>${account.id}</td>
                                    <td>${account.name}</td>
                                    <td>
                                        <#if account.status==0>
                                            正常
                                        <#elseif account.status==1>
                                            已经被锁定
                                        <#else>
                                            什么破状态！
                                        </#if>
                                    </td>
                                    <td>
                                        <#if account.status==0>
                                            <button class="btn btn-info btn-sm right"
                                                    onclick="javascript:location.href='/account/lock?aid=${account.id}'">锁定账号
                                            </button>
                                        <#elseif account.status==1>
                                            <button class="btn btn-info btn-sm right"
                                                    onclick="javascript:location.href='/account/unlock?aid=${account.id}'">解锁账号
                                            </button>
                                        <#else>
                                            什么破状态！
                                        </#if>
                                    </td>

                                </tr>
                            </tbody>
                        </table>
                    </div>
                </div>
                <!-- /.box-body -->
            </div>
        </div>
        </div>
    </section>
</aside>
</@layout.mainLayout>
<script type="text/javascript">
    $(function () {
                $("#begin").datepicker().datepicker("option", {
                    "dateFormat": "yy-mm-dd"
                });
                ;
            }
    );
</script>
