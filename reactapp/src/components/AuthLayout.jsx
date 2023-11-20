import { Box, Container, Flex } from '@chakra-ui/react'
import React from 'react'
import { Outlet } from 'react-router-dom'
import { Navbar } from './AuthNav'

export const AuthLayout = () => {
    return (
        <Flex direction="column" flex="1" bg={"#ddd"} minH={"calc(100vh - var(--chakra-sizes-16))"}>
            <Navbar />
            <Flex as="main" role="main" direction="column" flex="1">
                <Box px={{ base: 4, md: 16 }} py={6} flex="1">
                    <Outlet />
                </Box>
            </Flex>
            {/* <Footer /> */}
        </Flex>
    )
}
