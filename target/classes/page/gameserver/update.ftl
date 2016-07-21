<#import "layouts/layout.ftl" as layout />
<@layout.mainLayout>
<aside class="right-side">
    <section class="content">
        <div class="col-md-9">
            <div class="box box-primary">
                <div class="box-body">
                    <form action="/gameserver/update" method="post">
                        <div class="input-group margin">
                            <input type="hidden" name="id" value="${gameServer.id}"/>

                            <div class="form-group form-inline">
                                <label>服务器名称</label>
                                <input type="text" class="form-control" name="name" value="${gameServer.name}"/>
                            </div>
                            <div class="form-group form-inline">
                                <label>服务器ip地址</label>
                                <input type="text" class="form-control" name="ip" value="${gameServer.ip}"/>
                            </div>
                            <div class="form-group form-inline">
                                <label>服务器端口</label>
                                <input type="text" class="form-control" name="port" value="${gameServer.port?number}"/>
                            </div>

                            <div class="form-group">
                                <label>开服时间</label>

                                <div class="input-group">
                                    <div class="input-group-addon">
                                        <i class="fa fa-calendar"></i>
                                    </div>
                                    <input id="begin" type="text" name="opentime" class="form-control" value="${gameServer.opentime}"/>
                                </div>
                                <!-- /.input group -->
                            </div>
                            <div class="form-group">
                                <label>服务器状态</label>
                                <select class="form-control" name="status">
                                    <option value="0"
                                            <#if gameServer.status==0>selected </#if>
                                            >等待开服
                                    </option>
                                    <option value="1"
                                            <#if gameServer.status==1>selected </#if>
                                            >正常运行中
                                    </option>
                                    <option value="2"
                                            <#if gameServer.status==2>selected </#if>
                                            >维护中
                                    </option>
                                    <option value="-1"
                                            <#if gameServer.status==-1>selected </#if>
                                            >停服
                                    </option>
                                </select>
                            </div>
                            <!-- /.input group -->

                            <div class="form-group">
                                <button type="submit" class="btn btn-primary"
                                        onclick="this.parentNode.submit(); return false;">更新服务器信息
                                </button>
                            </div>
                        </div>
                </div>
                </form>
                <!-- /input-group -->
            </div>
        </div>
        </div>
    </section>
</aside>
</@layout.mainLayout>
<script type="text/javascript">
    $
    $(function () {
                $("#begin").datepicker({
                    "dateFormat":"yy-mm-dd"
                });
            }
    );
</script>
