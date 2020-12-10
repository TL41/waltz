/*
 * Waltz - Enterprise Architecture
 * Copyright (C) 2016, 2017, 2018, 2019 Waltz open source project
 * See README.md for more information
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific
 *
 */

import template from "./complexity-basic-info-tile.html";
import {initialiseData} from "../../../common";
import {CORE_API} from "../../../common/services/core-api-utils";
import {determineDownwardsScopeForKind, mkSelectionOptions} from "../../../common/selector-utils";
import {calcComplexitySummary} from "../../services/complexity-utilities";


const bindings = {
    parentEntityRef: "<",
    filters: "<?"
};


const initialState = {
    filters: {}
};



function controller(serviceBroker) {

    const vm = initialiseData(this, initialState);

    vm.$onChanges = () => {
        const selector = mkSelectionOptions(
            vm.parentEntityRef,
            determineDownwardsScopeForKind(vm.parentEntityRef.kind),
            undefined,
            vm.filters);


        serviceBroker
            .loadViewData(
                CORE_API.ComplexityStore.findBySelector,
                [ selector ])
            .then(r => vm.stats = calcComplexitySummary(r.data));
    };

}

controller.$inject = [
    "ServiceBroker"
];


const component = {
    template,
    bindings,
    controller
};


export default {
    id: "waltzComplexityBasicInfoTile",
    component
};